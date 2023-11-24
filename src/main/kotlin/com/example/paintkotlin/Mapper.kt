package com.example.paintkotlin

import com.example.paintkotlin.dto.*
import javafx.scene.paint.Paint
import javafx.scene.shape.*

interface Mapper<T, E> {
    fun mapToDto(input: T): E?
    fun mapFromDto(input: E): T?
}

//fun <T, R> T.map(mapper: (T) -> (R)): R = mapper(this)   // добить

fun getShapeMapper(): Mapper<Shape, ShapeDTO> {
    return ShapeMapperImpl()
}

private class ShapeMapperImpl : Mapper<Shape, ShapeDTO> {
    override fun mapToDto(source: Shape): ShapeDTO? {
        return when (source) {
            is Rectangle -> mapToRectangleDto(source)
            is Triangle -> mapToTriangleDto(source)
            is Ellipse -> mapToEllipseDto(source)
            is Circle -> mapToCircleDto(source)
            is Line -> mapToLineDto(source)
            is Star -> mapToStarDto(source)
            else -> null
        }?.apply {
            this.layoutX = source.layoutX
            this.layoutY = source.layoutY
        }
    }

    override fun mapFromDto(source: ShapeDTO): Shape? {
        return when (source) {
            is RectangleDTO -> mapFromRectangleDto(source)
            is TriangleDTO -> mapFromTriangleDto(source)
            is EllipseDTO -> mapFromEllipseDto(source)
            is CircleDTO -> mapFromCircleDto(source)
            is LineDTO -> mapFromLineDto(source)
            is StarDTO -> mapFromStarDto(source)
            else -> null
        }?.apply {
            this.layoutX = source.layoutX ?: 0.0
            this.layoutY = source.layoutY ?: 0.0
            this.fill = Paint.valueOf(source.fill)
            this.stroke = Paint.valueOf(source.stroke)
            this.strokeWidth = source.strokeWidth
        }
    }

    private fun mapToRectangleDto(source: Rectangle): RectangleDTO = RectangleDTO(source)
    private fun mapToLineDto(source: Line): LineDTO = LineDTO(source)
    private fun mapToTriangleDto(source: Triangle): TriangleDTO = TriangleDTO(source)

    private fun mapToStarDto(source: Star): StarDTO = StarDTO(source)
    private fun mapToCircleDto(source: Circle): CircleDTO = CircleDTO(source)
    private fun mapToEllipseDto(source: Ellipse): EllipseDTO = EllipseDTO(source)

    private fun mapFromRectangleDto(source: RectangleDTO): Rectangle = Rectangle().apply {
        x = source.x
        y = source.y
        width = source.width
        height = source.height
    }

    private fun mapFromLineDto(source: LineDTO): Line = Line().apply {
        startX = source.startX
        startY = source.startY
        endX = source.endX
        endY = source.endY
    }

    private fun mapFromTriangleDto(source: TriangleDTO): Triangle = Triangle().apply {
        points.addAll(source.points)
    }

    private fun mapFromStarDto(source: StarDTO): Star = Star().apply {
        points.addAll(source.points)
    }

    private fun mapFromCircleDto(source: CircleDTO): Circle = Circle().apply {
        centerX = source.centerX
        centerY = source.centerY
        radius = source.radius
    }

    private fun mapFromEllipseDto(source: EllipseDTO): Ellipse = Ellipse().apply {
        centerX = source.centerX
        centerY = source.centerY
        radiusX = source.radiusX
        radiusY = source.radiusY
    }
}
