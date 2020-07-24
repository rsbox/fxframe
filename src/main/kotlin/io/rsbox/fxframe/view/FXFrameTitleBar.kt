package io.rsbox.fxframe.view

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import io.rsbox.fxframe.FXFrameStyle
import io.rsbox.fxframe.controller.FXFrameController
import io.rsbox.fxframe.graphic.TitleBarIconFactory
import tornadofx.*

/**
 * Represents the top title bar and menu in the viewport.
 */
class FXFrameTitleBar : View() {

    /**
     * Injected lazy controller.
     */
    private val controller: FXFrameController by inject()

    internal var disableDrag = false

    override val root = hbox(spacing = 12) {
        styleClass.add("titleBar")

        alignment = Pos.CENTER_LEFT

        if(controller.icon != null) {
            imageview(controller.icon!!) {
                fitWidth = 14.0
                fitHeight = 14.0
            }
        }

        text(primaryStage.titleProperty()) {
            styleClass.add("title")

            font = Font(12.0)
        }

        /**
         * Menu buttons
         */


        hbox(spacing = 6) {
            alignment = Pos.CENTER_RIGHT
            hgrow = Priority.ALWAYS
            maxWidth = Double.MAX_VALUE

            /**
             * Minimize Window Button
             */
            imageview(TitleBarIconFactory.minimizeIcon(16, FXFrameStyle.white)) {
                isPickOnBounds = true
            }

            /**
             * Maximize Button
             */
            imageview(TitleBarIconFactory.maximizeIcon(16, FXFrameStyle.white)) {
                isPickOnBounds = true
            }

            /**
             * Close Window Button
             */
            imageview(TitleBarIconFactory.closeIcon(16, FXFrameStyle.white)) {
                isPickOnBounds = true
            }
        }
    }
}