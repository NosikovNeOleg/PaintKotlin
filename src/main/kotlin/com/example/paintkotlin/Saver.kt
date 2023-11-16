package com.example.paintkotlin

import com.google.gson.Gson
import javafx.scene.shape.Shape
import java.io.*

interface Saver {
    //fun load(file: File) : List<Any>
}

object ShapesSaver : Saver {


    fun save(file: File, input: List<Shape>) {
        //val dtos = ArrayList<ShapeDTO>()
//        dtos.addAll(input.map{
//            ShapeMapper.mapShapeToDto(shape = it)!!
//        })
        FileOutputStream(file, false).use {
            it.write(Gson().toJson(input).toByteArray())

        }
    }

    fun load(file: File): List<Shape> {
        return FileInputStream(file).use { o ->
            Gson().fromJson(o.readBytes().toString(), List::class.java) as List<Shape>
        }
        //return list.map { ShapeMapper.mapDtoToShape(it)!! }
    }
}