package io.rsbox.fxframe.view

import javafx.scene.Cursor
import io.rsbox.fxframe.controller.FXFrameController
import tornadofx.View
import tornadofx.borderpane

/**
 * Represents the container view for the Borderless frame.
 */
class FXFrameViewport : View() {

    private val controller: FXFrameController by inject()

    /**
     * Resizing delta vars
     */

    private var dx: Double = 0.0
    private var dy: Double = 0.0
    private var dw: Double = 0.0
    private var dh: Double = 0.0

    private var resizeNorth = false
    private var resizeEast = false
    private var resizeSouth = false
    private var resizeWest = false

    init {
        titleProperty.bind(controller.initView.titleProperty)
    }

    override val root = borderpane {
        styleClass.add("viewport")

        /**
         * Add the titlebar fragment
         */
        top = find<FXFrameTitleBar>().root

        /**
         * Add the initView set in the controller
         */
        center = controller.initView.root

        /**
         * Resize Controls
         */
        setOnMouseMoved {
            var west = false
            var east = false
            var north = false
            var south = false

            if(it.screenX > primaryStage.x - 10 && it.screenX < primaryStage.x + 10) {
                west = true
            }

            if(it.screenX > (primaryStage.x + primaryStage.width - 10) && it.screenX < (primaryStage.x + primaryStage.width + 10)) {
                east = true
            }

            if(it.screenY > primaryStage.y - 10 && it.screenY < primaryStage.y + 10) {
                north = true
            }

            if(it.screenY > (primaryStage.y + primaryStage.height - 10) && it.screenY < (primaryStage.y + primaryStage.height + 10)) {
                south = true
            }

            when {
                west -> {
                    cursor = when {
                        north -> {
                            Cursor.NW_RESIZE
                        }
                        south -> {
                            Cursor.SW_RESIZE
                        }
                        else -> {
                            Cursor.W_RESIZE
                        }
                    }
                }
                east -> {
                    cursor = when {
                        north -> {
                            Cursor.NE_RESIZE
                        }
                        south -> {
                            Cursor.SE_RESIZE
                        }
                        else -> {
                            Cursor.E_RESIZE
                        }
                    }
                }
                else -> {
                    cursor = when {
                        north -> Cursor.N_RESIZE
                        south -> Cursor.S_RESIZE
                        else -> Cursor.DEFAULT
                    }
                }
            }
        }

        /**
         * Resize click
         */
        setOnMousePressed {
            var west = false
            var east = false
            var north = false
            var south = false

            if (it.screenX > primaryStage.x - 10 && it.screenX < primaryStage.x + 10) {
                west = true
            }

            if (it.screenX > (primaryStage.x + primaryStage.width - 10) && it.screenX < (primaryStage.x + primaryStage.width + 10)) {
                east = true
            }

            if (it.screenY > primaryStage.y - 10 && it.screenY < primaryStage.y + 10) {
                north = true
            }

            if (it.screenY > (primaryStage.y + primaryStage.height - 10) && it.screenY < (primaryStage.y + primaryStage.height + 10)) {
                south = true
            }

            resizeNorth = north
            resizeSouth = south
            resizeWest = west
            resizeEast = east

            dx = it.screenX
            dy = it.screenY
            dw = primaryStage.width
            dh = primaryStage.height
        }

        setOnMouseDragged {
            if(resizeNorth) {
                primaryStage.y = it.screenY
                primaryStage.height = dh + (dy - it.screenY)
            }

            if(resizeWest) {
                primaryStage.x = it.screenX
                primaryStage.width = dw +(dx - it.screenX)
            }


            if(resizeEast) {
                primaryStage.width = dw + (it.screenX - dx)
            }

            if(resizeSouth) {
                primaryStage.height = dh + (it.screenY - dy)
            }
        }
    }
}