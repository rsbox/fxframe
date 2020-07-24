package io.rsbox.fxframe.controller

import io.rsbox.fxframe.view.FXFrameViewport
import io.rsbox.fxframe.view.TransparentWindowView
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Rectangle2D
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.input.MouseButton
import javafx.stage.Modality
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*

/**
 * The FXFrame borderless frame window controller.
 */
internal class FXFrameController : Controller() {

    /**
     * The initial view object to load.
     */
    internal var initView: UIComponent by singleAssign()

    /**
     * The stage / window icon.
     */
    internal var icon: Image? = null

    internal var transparentWindow: Stage? = null

    /**
     * Interaction Control Logic Start.
     */

    private var prevSizeX = 0.0
    private var prevSizeY = 0.0

    private var prevPosX = 0.0
    private var prevPosY = 0.0

    val maximized = SimpleBooleanProperty(false)
    val resizable = SimpleBooleanProperty(true)
    val snappable = SimpleBooleanProperty(false)

    private var snapped = false

    lateinit var moveHandle: Node
    val viewport: FXFrameViewport by inject()

    private val stage get() = primaryStage

    /**
     * Initializes the controller window controls.
     */
    internal fun init() {
        prevSizeX = stage.width
        prevSizeY = stage.height
        prevPosX = stage.x
        prevPosY = stage.y

        transparentWindow = find<TransparentWindowView>().openModal(StageStyle.TRANSPARENT, modality = Modality.NONE)
        transparentWindow!!.hide()

        this.initMoveControl()
    }

    private fun initMoveControl() {
        var dx = 0.0
        var dy = 0.0

        var startX = 0.0
        var startY = 0.0

        /**
         * Event listeners.
         */
        moveHandle.setOnMousePressed {
            if(it.isPrimaryButtonDown) {
                dx = it.sceneX
                dy = it.sceneY

                if(maximized.get() || snapped) {
                    dx = prevSizeX * (it.sceneX / stage.width)
                    dy = prevSizeY * (it.sceneY / stage.height)
                } else {
                    prevSizeX = stage.width
                    prevSizeY = stage.height
                    prevPosX = stage.x
                    prevPosY = stage.y
                }

                startX = it.screenX
                startY = moveHandle.prefHeight(stage.height)
            }
        }

        moveHandle.setOnMouseDragged {
            if(it.isPrimaryButtonDown) {

                stage.x = it.screenX - dx

                if(snapped) {
                    if(it.screenX > startY) {
                        snapOff()
                    } else {
                        val scr = Screen.getScreensForRectangle(it.screenX, it.screenY, 1.0, 1.0)[0].visualBounds
                        stage.height = scr.height
                    }
                } else {
                    stage.y = it.screenY - dy
                }

                /**
                 * Windows Aero Snap Off
                 */
                if(maximized.get()) {
                    stage.width = prevSizeX
                    stage.height = prevSizeY
                    setMaximized(false)
                }

                var toCloseWindow = false
                if(!snappable.get()) {
                    toCloseWindow = true
                } else {
                    val screens = Screen.getScreensForRectangle(it.screenX, it.screenY, 1.0, 1.0)
                    if(screens.isEmpty()) {
                        return@setOnMouseDragged
                    }

                    val scr = screens[0].visualBounds

                    /**
                     * Snap left
                     */
                    if(it.screenX <= scr.minX + 20) {
                        val ay = scr.minY
                        val ah = scr.height
                        val ax = scr.minX
                        val aw = scr.width / 2

                        this.animateViewport(ax, ay, aw, ah)
                    }

                    /**
                     * Snap Right
                     */
                    else if(it.screenX >= scr.maxX - 20) {
                        val aw = scr.width / 2
                        val ax = scr.maxX - aw
                        val ay = scr.minY
                        val ah = scr.height

                        this.animateViewport(ax, ay, aw, ah)
                    }

                    /**
                     * Snap top and bottom
                     */
                    else if(it.screenY <= scr.minY + 20 || it.screenY >= scr.maxY - 20) {
                        val ax = scr.minX
                        val ay = scr.minY
                        val aw = scr.width
                        val ah = scr.height

                        this.animateViewport(ax, ay, aw, ah)
                    }

                    else {
                        toCloseWindow = true
                    }
                }

                if(toCloseWindow) {
                    transparentWindow!!.close()
                }
            }
        }

        /**
         * Double click listener
         */
        moveHandle.setOnMouseClicked {
            if(snappable.get() && (MouseButton.PRIMARY.equals(it.button)) && (it.clickCount == 2)) {
                maximize()
            }
        }
    }

    private fun snapOff() {
        stage.width = prevSizeX
        stage.height = prevSizeY
        snapped = false
    }

    /**
     * Maximizes the window.
     */
    fun maximize() {
        /**
         * The visible bounding rectangle of the screen.
         */
        val screen: Rectangle2D = if(Screen.getScreensForRectangle(stage.x, stage.y, stage.width / 2, stage.height / 2).isEmpty()) {
            Screen.getScreensForRectangle(stage.x, stage.y, stage.width, stage.height)[0].visualBounds
        } else {
            Screen.getScreensForRectangle(stage.x, stage.y, stage.width / 2, stage.height / 2)[0].visualBounds
        }

        if(maximized.get()) {
            stage.width = prevSizeX
            stage.height = prevPosY
            stage.x = prevPosX
            stage.y = prevPosY
            setMaximized(false)
        } else {
            if(!snapped) {
                prevSizeX = stage.width
                prevSizeY = stage.height
                prevPosX = stage.x
                prevPosY = stage.y
            }
            else if(!screen.contains(prevPosX, prevPosY)) {
                if(prevSizeX > screen.width) {
                    prevSizeX = screen.width - 20
                }

                if(prevSizeY > screen.height) {
                    prevSizeY = screen.height - 20
                }

                prevPosX = screen.minX + (screen.width - prevSizeX) / 2
                prevPosY = screen.minY + (screen.height - prevSizeY) / 2
            }

            stage.x = screen.minX
            stage.y = screen.minY
            stage.width = screen.width
            stage.height = screen.height

            setMaximized(true)
        }
    }

    fun minimize() {
        stage.isIconified = true
    }

    private fun setMaximized(value: Boolean) {
        this.maximized.set(value)
        setResizable(!value)
    }

    private fun setResizable(value: Boolean) {
        resizable.set(value)
    }

    /**
     * Animates the viewport window to the passed method parameters
     */
    private fun animateViewport(x: Double, y: Double, width: Double, height: Double) {
        transparentWindow!!.x = x
        transparentWindow!!.y = y
        transparentWindow!!.width = width
        transparentWindow!!.height = height

        transparentWindow!!.show()

    }
}