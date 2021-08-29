package de.menkalian.monoceros.app.printing

import android.graphics.fonts.Font
import de.menkalian.vela.template.parser.ITemplateParser

class PrintRenderer(private val printInformation: PrintInformation) {
    private val defaultOffset = 10f
    private val defaultSpacing = 5f
    private val defaultLineWidth = 1f

    //region printing attributes
    private val leftOffset = defaultOffset
    private val rightOffset = defaultOffset

    private val topOffset = defaultOffset

    private val botOffset = defaultOffset

    private val lineSize = defaultLineWidth

    private val verticalSpacing = defaultSpacing
    //endregion

    private val titleText: String
    private val yearText: String
    private val bigCommentText: String?
    private val smallCommentText: String?

//    private val font: Font = Font.Builder() Font.createFont(Font.PLAIN, PrintRenderer::class.java.classLoader.getResourceAsStream("fonts/standard.ttf"))!!
//    private val titleFont: Font = font.(16f)
//    private val textFont: Font = font.deriveFont(14f)
//    private val smallFont: Font = font.deriveFont(10f)

    init {
        val templateParser = ITemplateParser.getDefaultImplementation()

        titleText = templateParser.parse(printInformation.titleTemplate).evaluate(printInformation.variables)
        yearText = templateParser.parse(printInformation.yearTemplate).evaluate(printInformation.variables)
        bigCommentText = printInformation.bigCommentTemplate?.run { templateParser.parse(this).evaluate(printInformation.variables) }
        smallCommentText = printInformation.smallCommentTemplate?.run { templateParser.parse(this).evaluate(printInformation.variables) }
    }

    private fun calculateLabelSize(): RectangleSize {
        TODO()
//        val titleFontMetrics = graphics2D.getFontMetrics(titleFont)
//        val textFontMetrics = graphics2D.getFontMetrics(textFont)
//        val smallFontMetrics = graphics2D.getFontMetrics(smallFont)
//
//        val titleMetrics = titleFontMetrics.getLineMetrics(titleText, graphics2D)
//        val yearMetrics = textFontMetrics.getLineMetrics(yearText, graphics2D)
//        val bigCommentMetrics = bigCommentText?.run { textFontMetrics.getLineMetrics(bigCommentText, graphics2D) }
//        val smallCommentMetrics = smallCommentText?.run { smallFontMetrics.getLineMetrics(smallCommentText, graphics2D) }
//
//        var height = 0.0f
//        height += lineSize
//        height += topOffset
//        height += titleMetrics.height
//        height += verticalSpacing
//        height += yearMetrics.height
//
//        if (bigCommentMetrics != null) {
//            height += verticalSpacing
//            height += bigCommentMetrics.height
//        }
//
//        if (smallCommentMetrics != null) {
//            height += verticalSpacing
//            height += smallCommentMetrics.height
//        }
//
//        height += botOffset
//        height += lineSize
//
//        val width =
//            lineSize + leftOffset +
//                    maxOf(
//                        titleFontMetrics.stringWidth(titleText),
//                        textFontMetrics.stringWidth(yearText),
//                        textFontMetrics.stringWidth(bigCommentText ?: ""),
//                        smallFontMetrics.stringWidth(smallCommentText ?: "")
//                    ) +
//                    rightOffset + lineSize
//
//        return RectangleSize(width, height)
    }

//    private fun renderLabel(graphics2D: Graphics2D, offsetX: Float, offsetY: Float, orientation: Orientation, labelSize: RectangleSize) {
//        val originalStroke = graphics2D.stroke
//        graphics2D.stroke = BasicStroke(lineSize)
//        graphics2D.drawRect(
//            offsetX.toInt(),
//            offsetY.toInt(),
//            labelSize.getWidth(orientation).toInt(),
//            labelSize.getHeight(orientation).toInt()
//        )
//        graphics2D.stroke = originalStroke
//
//        when (orientation) {
//            Orientation.HORIZONTAL -> renderLabelHorizontal(graphics2D, offsetX, offsetY)
//            Orientation.VERTICAL   -> renderLabelVertical(graphics2D, offsetX, offsetY, labelSize)
//        }
//    }
//
//    private fun renderLabelHorizontal(graphics2D: Graphics2D, offsetX: Float, offsetY: Float) {
//        // Render the real text
//        val titleFontMetrics = graphics2D.getFontMetrics(titleFont)
//        val textFontMetrics = graphics2D.getFontMetrics(textFont)
//        val smallFontMetrics = graphics2D.getFontMetrics(smallFont)
//
//        val titleMetrics = titleFontMetrics.getLineMetrics(titleText, graphics2D)
//        val yearMetrics = textFontMetrics.getLineMetrics(yearText, graphics2D)
//        val bigCommentMetrics = bigCommentText?.run { textFontMetrics.getLineMetrics(bigCommentText, graphics2D) }
//        val smallCommentMetrics = smallCommentText?.run { smallFontMetrics.getLineMetrics(smallCommentText, graphics2D) }
//
//        val addText = { font: Font, text: String, x: Float, y: Float ->
//            graphics2D.font = font
//            graphics2D.drawString(text, x, y)
//        }
//
//        var currentY = offsetY + topOffset
//        addText(
//            titleFont, titleText,
//            offsetX + leftOffset,
//            currentY + titleMetrics.height - titleMetrics.descent
//        )
//        currentY += titleMetrics.height + verticalSpacing
//
//        addText(
//            textFont, yearText,
//            offsetX + leftOffset,
//            currentY + yearMetrics.height - yearMetrics.descent
//        )
//        currentY += yearMetrics.height
//
//        if (bigCommentText != null && bigCommentMetrics != null) {
//            currentY += verticalSpacing
//            addText(
//                textFont, bigCommentText,
//                offsetX + leftOffset,
//                currentY + bigCommentMetrics.height - bigCommentMetrics.descent
//            )
//            currentY += bigCommentMetrics.height
//        }
//
//        if (smallCommentText != null && smallCommentMetrics != null) {
//            currentY += verticalSpacing
//            addText(
//                smallFont, smallCommentText,
//                offsetX + leftOffset,
//                currentY + smallCommentMetrics.height - smallCommentMetrics.descent
//            )
//            currentY += smallCommentMetrics.height
//        }
//    }
}