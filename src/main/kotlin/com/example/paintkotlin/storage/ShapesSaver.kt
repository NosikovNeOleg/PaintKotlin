package com.example.paintkotlin.storage

import com.example.paintkotlin.storage.ShapesStorage.getAllShapes
import com.example.paintkotlin.storage.dto.ShapeDTO
import javafx.scene.shape.Shape
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


interface Saver<T> {
    fun save(file: File)
    fun load(file: File)
}


fun getShapeSaver(): Saver<Shape> {
    return ShapesSaverImpl()
}

private class ShapesSaverImpl : Saver<Shape> {

    val shapeMapper: Mapper<Shape, ShapeDTO> = getShapeMapper()

    override fun save(file: File) {
        val shapes = getAllShapes()
        file.writeText(Json.encodeToString(shapes.map { shapeMapper.mapToDto(it) }))
    }


    override fun load(file: File) {
        val shapes : List<Shape> = Json.decodeFromString<List<ShapeDTO>>(file.readText()).map {
                shapeMapper.mapFromDto(it)
        }

    }
}
