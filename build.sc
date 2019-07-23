import mill._, scalalib._

object kvstore extends ScalaModule{
  def scalaVersion = "2.12.8"
  def millSourcePath = super.millSourcePath / os.up
  def ivyDeps = Agg(
//    ivy"io.grpc:grpc-all:1.10.0",
//    ivy"com.google.protobuf:protobuf-java:3.9.0",
//    ivy"com.thesamet.scalapb::scalapb-runtime:0.9.0",
    ivy"io.grpc:grpc-netty:1.22.1",
    ivy"com.thesamet.scalapb::scalapb-runtime-grpc:0.9.0"
  )
  def generatedSources = T{
    val protoPath = millSourcePath / "src" / "main" / "proto"
    os.proc(
        proto.scalapbc().path,
        os.walk(protoPath).filter(_.ext == "proto"),
        "--proto_path=" + protoPath,
//        "-v261",
        "--scala_out=grpc:" + T.ctx().dest,
        "--java_out=" + T.ctx().dest
      )
      .call()

    Seq(PathRef(T.ctx().dest))
  }

}
object proto extends Module{


  def zip = T{
    os.write(
      T.ctx().dest / "proto.zip",
      requests.get("https://github.com/scalapb/ScalaPB/releases/download/v0.9.0/scalapbc-0.9.0.zip")
      .data
      .bytes
    )
    os.proc("")
    PathRef(T.ctx().dest / "proto.zip")
  }
  def unpacked = T{
    os.proc("unzip", zip().path).call(cwd = T.ctx().dest)
    PathRef(T.ctx().dest)
  }
  def scalapbc = T{
    PathRef(unpacked().path / "scalapbc-0.9.0" / "bin" / "scalapbc")
  }
}