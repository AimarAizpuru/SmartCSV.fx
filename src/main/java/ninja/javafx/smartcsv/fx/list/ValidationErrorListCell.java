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

package ninja.javafx.smartcsv.fx.list;

import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import ninja.javafx.smartcsv.validation.ValidationError;

import java.util.ResourceBundle;

import static ninja.javafx.smartcsv.fx.util.I18nValidationUtil.getI18nValidatioMessage;

/**
 * Cell to show the error text
 */
public class ValidationErrorListCell extends ListCell<ValidationError> {

    private ResourceBundle resourceBundle;

    public ValidationErrorListCell(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void updateItem(ValidationError validationError, boolean empty) {
        super.updateItem(validationError, empty);
        if (empty) {
            clearContent();
        } else {
            addContent(validationError);
        }
    }


    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void addContent(ValidationError validationError) {
        setText(null);
        Text text = new Text(getI18nValidatioMessage(resourceBundle, validationError));
        text.setWrappingWidth(180);
        setGraphic(text);
    }
}
