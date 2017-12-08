package com.patres.converter.gui.dialog

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialog.DialogTransition
import com.patres.converter.MainApp
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Region
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*

class HandlerDialog(private val exception: String) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(HandlerDialog::class.java)
    }

    private var dialog: JFXDialog

    init {
        val dialogPane = MainApp.mainController.root
        dialog = JFXDialog(dialogPane, getDialogContent(), DialogTransition.CENTER)
    }

    fun getDialogContent(): Region? {
        var content: Region? = null
        try {
            val loader = FXMLLoader()
            loader.location = javaClass.getResource("/fxml/dialog/ExceptionHandlerDialog.fxml")
            loader.resources = getDialogBundle()
            loader.setController(DialogController(this, exception))
            content = loader.load<Region>()
        } catch (e: IOException) {
            LOGGER.error("I/O Exception", e)
        }

        return content
    }

    fun getDialogBundle(): ResourceBundle = MainApp.bundle


    fun show() = dialog.show()

    fun closeDialog() = dialog.close()

}
