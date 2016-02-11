cmake_minimum_required(VERSION 3.3)
project(ChatServer)


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -lboost_thread")

set(SOURCE_FILES main.cpp ChatServer.cpp ChatServer.h ChatHandler.cpp ChatHandler.h User.cpp User.h)
add_executable(ChatServer ${SOURCE_FILES})