package org.framefx

import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.framefx.controller.FXFrameController
import org.framefx.view.FXFrameViewport
import tornadofx.App
import tornadofx.UIComponent
import tornadofx.importStylesheet
import tornadofx.setStageIcon

/**
 * The Primary FXFrame JavaFX Application abstraction.
 */
abstract class FXFrameApp : App(FXFrameViewport::class, FXFrameStyle::class) {

    private val controller: FXFrameController by inject()

    /**
     * The skin theme for the FX borderless frame.
     */
    abstract val skin: FXFrameSkin

    /**
     * The primary view to load.
     */
    abstract val view: UIComponent

    /**
     * Sets the Stage window icon.
     */
    fun setFXFrameIcon(icon: Image) {
        setStageIcon(icon)
        controller.icon = icon
    }

    override fun init() {
        importStylesheet(skin.stylesheet)

        /**
         * Set the view
         */
        controller.initView = view

        super.init()
    }

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)
        stage.isResizable = true
        super.start(stage)
    }
}