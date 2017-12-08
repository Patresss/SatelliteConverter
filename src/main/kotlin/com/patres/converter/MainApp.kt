package com.patres.converter

import com.jfoenix.controls.JFXDecorator
import com.patres.converter.gui.MainController
import com.patres.converter.gui.dialog.ErrorDialog
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*


class MainApp : Application() {

    companion object {
        private val TITTLE = "Converter"
        private val SCENE_WIDTH = 600
        private val SCENE_HEIGHT = 250
        private val LOGGER = LoggerFactory.getLogger(MainApp::class.java)
        lateinit var mainStage: Stage
        lateinit var mainController: MainController
        var bundle = ResourceBundle.getBundle("language/Bundle", Locale("pl"))!!

        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApp::class.java)
        }
    }

    override fun start(stage: Stage) {
        try {
            settingsHandlerException()
            mainStage = stage
            mainStage.title = TITTLE
            mainStage.icons.add(Image("/image/icon.png"))

            mainStage.scene = createScene(loadMainPane())

            mainStage.minWidth = SCENE_WIDTH.toDouble()
            mainStage.minHeight = SCENE_HEIGHT.toDouble()
            mainStage.isResizable = false
            mainStage.show()
        } catch (e: IOException) {
            LOGGER.error("Error in start method - I/O Exception", e)
        }
    }

    private fun settingsHandlerException() {
        Thread.currentThread().setUncaughtExceptionHandler { _, throwable ->
            LOGGER.error("Error: {}", throwable)
            ErrorDialog.showAndWait(throwable)
        }
    }

    private fun createScene(mainPane: Pane): Scene {
        val jfxDecorator = JFXDecorator(mainStage, mainPane, false, false, true)
        val scene = Scene(jfxDecorator, SCENE_WIDTH.toDouble(), SCENE_HEIGHT.toDouble())
        setStyle(scene)
        return scene
    }

    @Throws(IOException::class)
    private fun loadMainPane(): Pane {
        val loader = FXMLLoader()
        loader.location = MainApp::class.java.getResource("/fxml/Main.fxml")
        loader.resources = bundle

        val mainPane = loader.load<Pane>()
        mainController = loader.getController()
        return mainPane
    }

    private fun setStyle(scene: Scene) {
        scene.stylesheets.add(MainApp::class.java.getResource("/css/style_day.css").toExternalForm())
    }


}

