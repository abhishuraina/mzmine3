/*
 * Copyright 2006-2020 The MZmine Development Team
 *
 * This file is part of MZmine.
 *
 * MZmine is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * MZmine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MZmine; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */

package io.github.mzmine.parameters.parametertypes.ranges;

import com.google.common.collect.Range;
import io.github.mzmine.datamodel.RawDataFile;
import io.github.mzmine.datamodel.Scan;
import io.github.mzmine.main.MZmineCore;
import io.github.mzmine.modules.tools.mzrangecalculator.MzRangeFormulaCalculatorModule;
import io.github.mzmine.modules.tools.mzrangecalculator.MzRangeMassCalculatorModule;
import io.github.mzmine.parameters.dialogs.ParameterSetupDialog;
import io.github.mzmine.parameters.parametertypes.selectors.RawDataFilesComponent;
import io.github.mzmine.parameters.parametertypes.selectors.RawDataFilesParameter;
import io.github.mzmine.parameters.parametertypes.selectors.ScanSelection;
import io.github.mzmine.parameters.parametertypes.selectors.ScanSelectionComponent;
import io.github.mzmine.parameters.parametertypes.selectors.ScanSelectionParameter;
import javafx.scene.control.Button;

public class MZRangeComponent extends DoubleRangeComponent {

  private final Button setAutoButton, fromMassButton, fromFormulaButton;

  public MZRangeComponent() {

    super(MZmineCore.getConfiguration().getMZFormat());

    // setBorder(BorderFactory.createEmptyBorder(0, 9, 0, 0));

    setAutoButton = new Button("Auto range");
    setAutoButton.setOnAction(e -> {
      RawDataFile currentFiles[] =
          MZmineCore.getProjectManager().getCurrentProject().getDataFiles();
      ScanSelection scanSelection = new ScanSelection();

      try {
        ParameterSetupDialog setupDialog = (ParameterSetupDialog) this.getScene().getWindow();
        RawDataFilesComponent rdc = (RawDataFilesComponent) setupDialog
            .getComponentForParameter(new RawDataFilesParameter());
        if (rdc != null) {
          RawDataFile matchingFiles[] = rdc.getValue().getMatchingRawDataFiles();
          if (matchingFiles.length > 0)
            currentFiles = matchingFiles;
        }
        ScanSelectionComponent ssc = (ScanSelectionComponent) setupDialog
            .getComponentForParameter(new ScanSelectionParameter());
        if (ssc != null)
          scanSelection = ssc.getValue();
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      Range<Double> mzRange = null;
      for (RawDataFile file : currentFiles) {
        Scan scans[] = scanSelection.getMatchingScans(file);
        for (Scan s : scans) {
          Range<Double> scanRange = s.getDataPointMZRange();
          if (scanRange == null)
            continue;
          if (mzRange == null)
            mzRange = scanRange;
          else
            mzRange = mzRange.span(scanRange);
        }
      }
      if (mzRange != null)
        setValue(mzRange);
    });
    RawDataFile currentFiles[] = MZmineCore.getProjectManager().getCurrentProject().getDataFiles();
    setAutoButton.setDisable(currentFiles.length == 0);
    add(setAutoButton, 3, 0);

    fromMassButton = new Button("From mass");
    fromMassButton.setOnAction(e -> {
      Range<Double> mzRange = MzRangeMassCalculatorModule.showRangeCalculationDialog();
      if (mzRange != null)
        setValue(mzRange);
    });
    add(fromMassButton, 4, 0);

    fromFormulaButton = new Button("From formula");
    fromFormulaButton.setOnAction(e -> {
      Range<Double> mzRange = MzRangeFormulaCalculatorModule.showRangeCalculationDialog();
      if (mzRange != null)
        setValue(mzRange);
    });
    add(fromFormulaButton, 5, 0);

  }

}
