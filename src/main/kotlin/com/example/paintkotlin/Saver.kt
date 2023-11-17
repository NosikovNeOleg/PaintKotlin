package com.example.paintkotlin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.scene.shape.Shape
import java.io.*
import java.lang.reflect.Type


interface Saver<T> {
    fun save(file: File, input: List<T>)
    fun load(file: File): List<T>
}


public fun getShapeSaver() : Saver<Shape> {
    return ShapesSaverImpl()
}
private class ShapesSaverImpl : Saver<Shape> {

    val shapeMapper : Mapper<Shape,ShapeDTO> = getShapeMapper()

    override fun save(file: File, input: List<Shape>) {
        file.writeText(Gson().toJson(input.map {
            shapeMapper.mapToDto(it)
        }))
    }


    override fun load(file: File): List<Shape> {
        return try {
            val type: Type = object : TypeToken<List<ShapeDTO?>?>() {}.type
            return (Gson().fromJson<List<ShapeDTO>>(file.readText(), type)).map {
                shapeMapper.mapFromDto(it)
            }
        } catch (e: ClassCastException) {
            listOf() // TODO доработать момент падения
        } catch (_: IllegalStateException) {
            listOf() // TODO доработать момент падения
        } catch (npe: NullPointerException) {
            listOf()// TODO доработать момент падения
        }
    }
}