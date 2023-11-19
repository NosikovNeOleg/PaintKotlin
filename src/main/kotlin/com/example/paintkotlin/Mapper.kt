package com.example.paintkotlin

import javafx.scene.shape.*

interface Mapper<T, E> {
    fun mapToDto(input: T): E?
    fun mapFromDto(input: E): T?
}

fun getShapeMapper(): Mapper<Shape, ShapeDTO> {
    return ShapeMapperImpl()
}

class ShapeMapperImpl : Mapper<Shape, ShapeDTO> {
    override fun mapToDto(source: Shape): ShapeDTO? {
        val (fill,stroke) = source
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
            this.layoutX = source.layoutX
            this.layoutY = source.layoutY
            this.fill = source.fill
            this.stroke = source.stroke
            this.strokeWidth = source.strokeWidth
        }
    }

    private fun mapToRectangleDto(source: Rectangle): RectangleDTO = RectangleDTO(source)
    private fun mapToLineDto(source: Line): LineDTO = LineDTO()
    private fun mapToTriangleDto(source: Triangle): TriangleDTO = TriangleDTO(
        source.stroke.toString(),
        source.fill.toString(),
        source.strokeWidth,
        points = source.points
    )

    private fun mapToStarDto(source: Star): StarDTO = StarDTO()
    private fun mapToCircleDto(source: Circle): CircleDTO = CircleDTO()
    private fun mapToEllipseDto(source: Ellipse): EllipseDTO = EllipseDTO()

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

private operator fun Shape.component2(): Any {
    return this.stroke
}

private operator fun Shape.component1(): Any {
    return this.fill
}
