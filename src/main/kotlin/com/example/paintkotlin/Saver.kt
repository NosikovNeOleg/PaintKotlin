package com.example.paintkotlin

import com.example.paintkotlin.dto.ShapeDTO
import javafx.scene.shape.Shape
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


interface Saver<T> {
    fun save(file: File, input: List<T>)
    fun load(file: File): List<T>
}


fun getShapeSaver(): Saver<Shape> {
    return ShapesSaverImpl()
}

private class ShapesSaverImpl : Saver<Shape> {

    val shapeMapper: Mapper<Shape, ShapeDTO> = getShapeMapper()

    override fun save(file: File, input: List<Shape>) {
        file.writeText(Json.encodeToString(input.map { shapeMapper.mapToDto(it) }))
    }


    override fun load(file: File): List<Shape> {
        return Json.decodeFromString<List<ShapeDTO>>(file.readText()).map {
                shapeMapper.mapFromDto(it)
            }
    }
}
