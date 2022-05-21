package gui

import gui.controllers.ControllerMain
import gui.views.ViewDescription
import gui.views.ViewHelp
import gui.views.view_DB.ViewDBEditor
import gui.views.view_diagnostics.ViewDiagnostics
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView: View() {
    private val controllerMain: ControllerMain by inject()
    private val pathToDB = SimpleStringProperty()

    override var root = vbox {
        title = "ПС для диагностики неисправностей"
        minWidth = 768.0
        minHeight = 500.0

        borderpane {
            paddingTop = 4.0
            paddingBottom = 4.0
            paddingLeft = 10.0
            paddingRight = 10.0

            left = borderpane {
                paddingRight = 10.0
                center = label("Путь до БЗ:")
            }

            center = textfield(pathToDB) {
                isFocusTraversable = true
                isEditable = false
                tooltip("Текущий путь до базы знаний")
            }

            right = hbox {

                hbox {
                    paddingLeft = 10.0
                    splitmenubutton("Открыть из...") {
                        tooltip("Загрузка путём выбора папки с БЗ или загрузка из текущего каталога")

                        item("Открыть из...") {
                            action {
                                val currentPath = chooseDirectory()
                                if (currentPath != null)
                                    pathToDB.value = currentPath.toString()

                                controllerMain.initDB(pathToDB.value)
                            }
                        }

                        item("Загрузить из текущего каталога") {
                            action {
                                controllerMain.initDB(pathToDB.value)
                            }
                        }

                        action {
                            val currentPath = chooseDirectory()
                            if (currentPath != null)
                                pathToDB.value = currentPath.toString()

                            controllerMain.initDB(pathToDB.value)
                        }
                    }
                }

                hbox {
                    paddingLeft = 10.0
                    splitmenubutton("Сохранить в...") {
                        tooltip("Сохранение путём выбора папки с БЗ или сохранение в текущий каталог")

                        item("Сохранить в...") {
                            action {
                                val currentPath = chooseDirectory()
                                if (currentPath != null)
                                    pathToDB.value = currentPath.toString()

                                controllerMain.saveDB(pathToDB.value)
                            }
                        }

                        item("Сохранить в текущий каталог") {
                            action {
                                controllerMain.saveDB(pathToDB.value)
                            }
                        }

                        action {
                            val currentPath = chooseDirectory()
                            if (currentPath != null)
                                pathToDB.value = currentPath.toString()

                            controllerMain.saveDB(pathToDB.value)
                        }
                    }
                }

            }
        }

        tabpane {
            minHeight = this@vbox.minHeight - this@vbox.borderpane().height

            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            fitToParentSize()

            tab("Описание БЗ") {
                this.content = ViewDescription().root
            }

            tab("Редактор БЗ") {
                this.content = ViewDBEditor().root
            }

            tab("Диагностика") {
                this.content = ViewDiagnostics().root
            }

            tab("Помощь") {
                this.content = ViewHelp().root
            }
        }

    }
}

