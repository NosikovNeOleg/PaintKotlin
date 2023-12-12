package com.example.paintkotlin.storage

import com.example.paintkotlin.presenters.shapeFactory
import com.example.paintkotlin.storage.ShapesStorage.getAllShapes
import com.example.paintkotlin.storage.ShapesStorage.getStorage
import com.example.paintkotlin.storage.dto.ShapeDTO
import javafx.scene.shape.Shape
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


interface Saver<T> {
    fun save(obj : T)
    fun saveAll(list : List<T>)
    fun saveToFIle(file: File)
    fun loadFromFile(file: File): List<Shape>
}


fun getShapeSaver(): Saver<Shape> {
    return ShapesSaverImpl()
}

private class ShapesSaverImpl : Saver<Shape> {

    val shapeMapper: Mapper<Shape, ShapeDTO> = getShapeMapper()
    val shapesStorage: ShapesStorage = getStorage()

    override fun save(shape: Shape) {
        shapesStorage.addShape(shape)
    }

    override fun saveAll(shapes: List<Shape>) {
        shapesStorage.addShapes(shapes)
    }

    override fun saveToFIle(file: File) {
        val shapes = shapesStorage.getAllShapes()
        file.writeText(Json.encodeToString(shapes.map { shapeMapper.mapToDto(it) }))
    }


    override fun loadFromFile(file: File) = Json.decodeFromString<List<ShapeDTO>>(file.readText()).map {
        shapeMapper.mapFromDto(it)
    }
}
