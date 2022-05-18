package windows

import windows.controllers.ControllerMain
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*
import windows.view_centers.ViewDB.ViewDBEditor
import windows.view_centers.ViewDescription
import windows.view_centers.ViewDiagnostics
import windows.view_centers.ViewHelp

class MainView: View() {
    private val controllerMain: ControllerMain by inject()
    private val pathToDB = SimpleStringProperty()

    override fun onDock() {
        currentWindow?.sizeToScene()
        super.onDock()
    }

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
                tooltip("Путь до базы знаний")
            }

            right = hbox {

                hbox {
                    paddingLeft = 10.0
                    splitmenubutton("Загрузить") {
                        tooltip("Загрузка по заданному пути / выбор папки с БЗ")

                        item("Загрузить") {
                            action {
                                controllerMain.initDB(pathToDB.value)
                            }
                        }

                        item("Открыть из...") {
                            action {
                                val currentPath = chooseDirectory()
                                if (currentPath != null)
                                    pathToDB.value = currentPath.toString()

                                controllerMain.initDB(pathToDB.value)
                            }
                        }

                        action {
                            controllerMain.initDB(pathToDB.value)
                        }
                    }
                }

                hbox {
                    paddingLeft = 10.0
                    splitmenubutton("Сохранить") {
                        tooltip("Сохранение по текущему пути / выбор папки с БЗ")

                        item("Сохранить") {
                            action {
                                controllerMain.saveDB(pathToDB.value)
                            }
                        }

                        item("Сохранить в...") {
                            action {
                                val currentPath = chooseDirectory()
                                if (currentPath != null)
                                    pathToDB.value = currentPath.toString()

                                controllerMain.saveDB(pathToDB.value)
                            }
                        }

                        action { controllerMain.saveDB(pathToDB.value) }
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

