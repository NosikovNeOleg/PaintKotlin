package com.example.paintkotlin

import com.example.paintkotlin.ShapeMapper.mapFromDto
import com.example.paintkotlin.ShapeMapper.mapToDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.scene.shape.Shape
import java.io.*
import java.lang.NullPointerException
import java.lang.reflect.Type


interface Saver<T> {
    fun save(file: File, input: T)
    fun load(file: File): T
}

interface ShapesSaver<T : List<Shape>> : Saver<T> {
    override fun save(file: File, input: T)
    override fun load(file: File): T
}

object ShapesSaverImpl : ShapesSaver<List<Shape>> {


    override fun save(file: File, input: List<Shape>) {
        file.writeText(Gson().toJson(input.map {
            mapToDto(it)
        }))
    }

    override fun load(file: File): List<Shape> {
        return try {
            val type: Type = object : TypeToken<List<ShapeDTO?>?>() {}.type
            return (Gson().fromJson<List<ShapeDTO>>(file.readText(), type)).map {
                mapFromDto(it)
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