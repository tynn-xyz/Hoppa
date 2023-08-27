cmake_minimum_required(VERSION 3.22.1)
project("bitmapinfo")
add_library(${CMAKE_PROJECT_NAME} SHARED AndroidBitmapInfo.cpp)
target_link_libraries(${CMAKE_PROJECT_NAME} jniGraphics)
