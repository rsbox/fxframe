package org.framefx.view

import javafx.geometry.Pos
import javafx.scene.input.MouseButton
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import org.framefx.FXFrameStyle
import org.framefx.controller.FXFrameController
import org.framefx.graphic.TitleBarIconFactory
import tornadofx.*
import kotlin.system.exitProcess

/**
 * Represents the top title bar and menu in the viewport.
 */
class FXFrameTitleBar : Fragment() {

    /**
     * Injected lazy controller.
     */
    private val controller: FXFrameController by inject()

    /**
     * Mouse Dragging offsets.
     */
    private var dx = 0.0
    private var dy = 0.0

    private var disableDrag = false

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


        hbox(spacing = 14) {
            alignment = Pos.CENTER_RIGHT
            hgrow = Priority.ALWAYS
            maxWidth = Double.MAX_VALUE

            /**
             * Minimize Window Button
             */
            imageview(TitleBarIconFactory.minimizeIcon(16, FXFrameStyle.white)) {

                isPickOnBounds = true

                /**
                 * Events
                 */
                setOnMouseEntered {
                    this.image = TitleBarIconFactory.minimizeIcon(16, FXFrameStyle.whiteDark)
                    disableDrag = true
                }

                setOnMouseExited {
                    this.image = TitleBarIconFactory.minimizeIcon(16, FXFrameStyle.white)
                    disableDrag = false
                }

                onLeftClick {
                    this.image = TitleBarIconFactory.minimizeIcon(16, FXFrameStyle.white)
                    primaryStage.isIconified = true
                }
            }

            /**
             * Maximize Button
             */
            imageview(TitleBarIconFactory.maximizeIcon(16, FXFrameStyle.white)) {
                isPickOnBounds = true

                /**
                 * Bindings
                 */
                var useRestoreIcon = false

                primaryStage.maximizedProperty().onChange {
                    if(it) {
                        useRestoreIcon = true
                        this.image = TitleBarIconFactory.restoreIcon(16, FXFrameStyle.white)
                    } else {
                        useRestoreIcon = false
                        this.image = TitleBarIconFactory.maximizeIcon(16, FXFrameStyle.white)
                    }
                }

                /**
                 * Events
                 */

                setOnMouseEntered {
                    this.image = when(useRestoreIcon) {
                        true -> TitleBarIconFactory.restoreIcon(16, FXFrameStyle.whiteDark)
                        false -> TitleBarIconFactory.maximizeIcon(16, FXFrameStyle.whiteDark)
                    }

                    disableDrag = true
                }

                setOnMouseExited {
                    this.image = when(useRestoreIcon) {
                        true -> TitleBarIconFactory.restoreIcon(16, FXFrameStyle.white)
                        false -> TitleBarIconFactory.maximizeIcon(16, FXFrameStyle.white)
                    }

                    disableDrag = false
                }

                setOnMouseClicked {
                    when(useRestoreIcon) {
                        true -> { primaryStage.isMaximized = false }
                        false -> { primaryStage.isMaximized = true }
                    }
                }
            }

            /**
             * Close Window Button
             */
            imageview(TitleBarIconFactory.closeIcon(16, FXFrameStyle.white)) {
                isPickOnBounds = true

                /**
                 * Events
                 */
                setOnMouseEntered {
                    this.image = TitleBarIconFactory.closeIcon(16, FXFrameStyle.whiteDark)
                    disableDrag = true
                }

                setOnMouseExited {
                    this.image = TitleBarIconFactory.closeIcon(16, FXFrameStyle.white)
                    disableDrag = false
                }

                onLeftClick {
                    exitProcess(0)
                }
            }
        }

        /**
         * Drag Window Controls.
         */

        setOnMousePressed {
            dx = primaryStage.x - it.screenX
            dy = primaryStage.y - it.screenY
        }

        setOnMouseDragged {
            if(primaryStage.isMaximized) {
                primaryStage.isMaximized = false
            }


            if(!disableDrag) {
                primaryStage.x = it.screenX + dx
                primaryStage.y = it.screenY + dy

                if((primaryStage.x + primaryStage.width) < it.screenX) {
                    primaryStage.x = it.screenX - (primaryStage.width / 2)
                }
            }
        }

        /**
         * Double click listener
         */
        setOnMouseClicked {
            if(it.button == MouseButton.PRIMARY) {
                if(it.clickCount == 2) {
                    primaryStage.isMaximized = !primaryStage.isMaximized
                }
            }
        }
    }
}