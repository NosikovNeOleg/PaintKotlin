package com.example.paintkotlin

import javafx.scene.shape.*

interface Mapper<T, E> {
    fun mapToDto(input: T): E?
    fun mapFromDto(input: E): T
}

fun getShapeMapper(): Mapper<Shape, ShapeDTO> {
    return ShapeMapperImpl()
}

class ShapeMapperImpl : Mapper<Shape, ShapeDTO> {
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

        }
    }

    override fun mapFromDto(source: ShapeDTO): Shape {
        return when (source) {
            is RectangleDTO -> mapFromRectangleDto(source)
            is TriangleDTO -> mapFromTriangleDto(source)
            is EllipseDTO -> mapFromEllipseDto(source)
            is CircleDTO -> mapFromCircleDto(source)
            is LineDTO -> mapFromLineDto(source)
            is StarDTO -> mapFromStarDto(source)
            else -> null
        }.apply {
            this.layoutX = source.layoutX
            this.layoutY = source.layoutY
        }
    }

    private fun mapToRectangleDto(source: Rectangle): RectangleDTO = RectangleDTO(source)
    private fun mapToLineDto(source: Line): LineDTO = LineDTO()
    private fun mapToTriangleDto(source: Triangle): TriangleDTO = TriangleDTO(
        stroke = source.stroke.toString(),
        fill = source.fill.toString(),
        strokeWidth = source.strokeWidth,
        points = source.points
    )

    private fun mapToStarDto(source: Star): StarDTO = StarDTO()
    private fun mapToCircleDto(source: Circle): CircleDTO = CircleDTO()
    private fun mapToEllipseDto(source: Ellipse): EllipseDTO = EllipseDTO()

    private fun mapFromRectangleDto(source: RectangleDTO): Rectangle = Rectangle()
    private fun mapFromLineDto(source: LineDTO): Line = Line()
    private fun mapFromTriangleDto(source: TriangleDTO): Triangle = Triangle()
    private fun mapFromStarDto(source: StarDTO): Star = Star()
    private fun mapFromCircleDto(source: CircleDTO): Circle = Circle()
    private fun mapFromEllipseDto(source: EllipseDTO): Ellipse = Ellipse()
}
