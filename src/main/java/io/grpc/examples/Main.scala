package io.grpc.examples

import io.grpc.examples.proto.kvstore.{CreateRequest, CreateResponse, DeleteRequest, DeleteResponse, KeyValueServiceGrpc, RetrieveRequest, RetrieveResponse, UpdateRequest, UpdateResponse}

import scala.concurrent.Future

object Main{

  def main(args: Array[String]): Unit = {

    val server = io.grpc.ServerBuilder
      .forPort(31337)
      .addService(
        KeyValueServiceGrpc.bindService(new Server(), concurrent.ExecutionContext.global)
      )
      .build()
      .start()

    val channel = io.grpc.ManagedChannelBuilder
      .forAddress("localhost", 31337)
      .usePlaintext(true)
      .build()

    val client = new Client(channel)
    println(client.create(CreateRequest()))
    println(client.retrieve(RetrieveRequest()))
    println(client.update(UpdateRequest()))
    println(client.delete(DeleteRequest()))
    println("Hello!")
  }
}

class Client(channel: io.grpc.Channel)
extends io.grpc.examples.proto.kvstore.KeyValueServiceGrpc.KeyValueServiceBlockingStub(channel)

class Server()
extends io.grpc.examples.proto.kvstore.KeyValueServiceGrpc.KeyValueService() {
  def create(request: CreateRequest): Future[CreateResponse] = {
    Future.successful(CreateResponse())
  }

  def retrieve(request: RetrieveRequest): Future[RetrieveResponse] = {
    Future.successful(RetrieveResponse())
  }

  def update(request: UpdateRequest): Future[UpdateResponse] = {
    Future.successful(UpdateResponse())
  }

  def delete(request: DeleteRequest): Future[DeleteResponse] = {
    Future.successful(DeleteResponse())
  }
}