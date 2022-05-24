package gui.views

import gui.controllers.ControllerHelp
import tornadofx.View
import tornadofx.asObservable
import tornadofx.borderpane
import tornadofx.listview

class ViewHelp: View() {
    private val sideController: ControllerHelp by inject()

    override val root = borderpane {
        center = listview(sideController.getHelpList().asObservable()) {

        }
    }
}