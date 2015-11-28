/*
   The MIT License (MIT)
   -----------------------------------------------------------------------------

   Copyright (c) 2015 javafx.ninja <info@javafx.ninja>

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in
   all copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
   THE SOFTWARE.

*/

package ninja.javafx.smartcsv.fx.table;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import ninja.javafx.smartcsv.fx.table.model.CSVRow;
import ninja.javafx.smartcsv.fx.table.model.CSVValue;

import static javafx.application.Platform.runLater;

/**
 * Created by Andreas on 27.11.2015.
 */
public class EditableValidationCell extends TableCell<CSVRow, CSVValue> {

    private ValueTextField textField;

    @Override
    public void startEdit() {
        super.startEdit();
        setTextField();
        runLater(() -> {
            textField.requestFocus();
            textField.selectAll();
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getValue());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(CSVValue item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || item.getValid().isValid() || isEditing()) {
            setStyle("");
            setTooltip(null);
        } else if (!item.getValid().isValid()) {
            setStyle("-fx-background-color: #ff8888");
            setTooltip(new Tooltip(item.getValid().error()));
        }

        if (item == null || empty) {
            setTextInCell(null);
        } else {
            if (isEditing()) {
                setTextField();
                textField.setValue(item);
            } else {
                setTextInCell(item.getValue());
            }
        }
    }

    private void setTextField() {
        if (textField == null) {
            createTextField();
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private void setTextInCell(String text) {
        setGraphic(null);
        setText(text);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    private void createTextField() {
        textField = new ValueTextField(getItem());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getValue());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && textField != null) {
                commitEdit(textField.getValue());
            }
        });
    }

    private class ValueTextField extends TextField {
        private CSVValue value;

        public ValueTextField(CSVValue value) {
            setValue(value);
        }

        public void setValue(CSVValue value) {
            this.value = value;
            setText(value.getValue());
        }

        public CSVValue getValue() {
            value.setValue(getText());
            return value;
        }

    }
}
