package com.patres.converter.gui

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXCheckBox
import com.jfoenix.controls.JFXSpinner
import com.jfoenix.controls.JFXTextField
import com.patres.converter.ExcelCreator
import com.patres.converter.MainApp
import com.patres.converter.geo.Loader
import com.patres.converter.gui.dialog.HandlerDialog
import javafx.fxml.FXML
import javafx.scene.layout.StackPane
import javafx.stage.FileChooser
import org.slf4j.LoggerFactory
import java.io.File


class MainController {


    companion object {
        private val LOGGER = LoggerFactory.getLogger(MainController::class.java)
    }

    // ================================================================================
    // Components
    // ================================================================================
    @FXML
    lateinit var root: StackPane

    @FXML
    private lateinit var filePathField: JFXTextField

    @FXML
    private lateinit var convertButton: JFXButton

    @FXML
    private lateinit var chooseMinute: JFXCheckBox


    // ================================================================================
    // Configuration methods
    // ================================================================================
    fun initialize() {
        convertButton.disableProperty().set(true)
        filePathField.textProperty().addListener({ _, _, newValue -> convertButton.disableProperty().set(!fileExist(newValue)) })
    }

    fun browseFile() {
        val fileChooser = FileChooser()
        fileChooser.title = "Wybierz plik"
        val file = fileChooser.showOpenDialog(MainApp.mainStage)
        if (file != null) {
            filePathField.text = file.absolutePath
        }
    }

    fun convert() {
        val pathTarget = filePathField.text
        val extFilter = FileChooser.ExtensionFilter("Excel  file (*.xlsx)", "*.xlsx")
        val fileChooser = FileChooser()
        fileChooser.extensionFilters.add(extFilter)
        fileChooser.initialDirectory = File(pathTarget).parentFile
        val file = fileChooser.showSaveDialog(MainApp.mainStage)

        if (file != null) {
            val loader = Loader(File(filePathField.text))
            val loadLines = loader.getLoadLines(chooseMinute.isSelected)
            val ex = ExcelCreator(loadLines, !chooseMinute.isSelected)
            ex.createExcel(file)
            HandlerDialog("Plik przkonwertowany").show()
        }
    }

    private fun fileExist(file: String): Boolean {
        return File(file).exists()
    }
}