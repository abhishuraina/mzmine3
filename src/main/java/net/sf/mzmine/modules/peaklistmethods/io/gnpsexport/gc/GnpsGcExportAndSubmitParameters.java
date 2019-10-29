/*
 * Copyright 2006-2018 The MZmine 2 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with MZmine 2; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
/*
 * This module was prepared by Abi Sarvepalli, Christopher Jensen, and Zheng Zhang at the Dorrestein
 * Lab (University of California, San Diego).
 * 
 * It is freely available under the GNU GPL licence of MZmine2.
 * 
 * For any questions or concerns, please refer to:
 * https://groups.google.com/forum/#!forum/molecular_networking_bug_reports
 * 
 * Credit to the Du-Lab development team for the initial commitment to the MGF export module.
 */

package net.sf.mzmine.modules.peaklistmethods.io.gnpsexport.gc;

import java.awt.Window;
import net.sf.mzmine.parameters.Parameter;
import net.sf.mzmine.parameters.dialogs.ParameterSetupDialog;
import net.sf.mzmine.parameters.impl.SimpleParameterSet;
import net.sf.mzmine.parameters.parametertypes.BooleanParameter;
import net.sf.mzmine.parameters.parametertypes.MassListParameter;
import net.sf.mzmine.parameters.parametertypes.filenames.FileNameParameter;
import net.sf.mzmine.parameters.parametertypes.selectors.PeakListsParameter;
import net.sf.mzmine.parameters.parametertypes.submodules.OptionalModuleParameter;
import net.sf.mzmine.util.ExitCode;


public class GnpsGcExportAndSubmitParameters extends SimpleParameterSet {

  public static final PeakListsParameter PEAK_LISTS = new PeakListsParameter();

  public static final FileNameParameter FILENAME =
      new FileNameParameter("Filename", "Base name of the output files (.MGF and .CSV).", "mgf");

  public static final MassListParameter MASS_LIST = new MassListParameter();

  public static final OptionalModuleParameter<GnpsGcSubmitParameters> SUBMIT =
      new OptionalModuleParameter<GnpsGcSubmitParameters>("Submit to GNPS",
          "Directly submits a GNPS-GC job", new GnpsGcSubmitParameters());

  public static final BooleanParameter OPEN_FOLDER =
      new BooleanParameter("Open folder", "Opens the export folder", false);


  public GnpsGcExportAndSubmitParameters() {
    super(new Parameter[] {PEAK_LISTS, FILENAME, MASS_LIST, SUBMIT, OPEN_FOLDER});
  }

  @Override
  public ExitCode showSetupDialog(Window parent, boolean valueCheckRequired) {
    String message = "<html><strong>Export/Submit to GNPS-GC:</strong>"
        + "<p>The GNPS Export module was designed for the <strong>GC</strong> workflow on GNPS <a href=\"http://gnps.ucsd.edu\">http://gnps.ucsd.edu</a>.<br>"
        + "See the <a href=\"https://ccms-ucsd.github.io/GNPSDocumentation/gc-ms-documentation/\"><strong>GNPS-GC-MS documentation here</strong></a> and <strong>please cite</strong>:<br>"
        + "<ul>"
        + "<li>the preprint <strong>GC-MS</strong> on GNPS: Aksenov et al.: <a href=\"https://www.biorxiv.org/content/10.1101/812404v1\">bioRxiv 812404 (2019)</a>.</li>"
        + "<li>the <strong>GNPS</strong> paper: Wang et al.:<a href=\"https://www.nature.com/nbt/journal/v34/n8/full/nbt.3597.html\">Nature Biotechnology 34.8 (2016): 828-837</a></li>"
        + "<li>and the <strong>MZmine</strong> paper: Pluskal et al.: <a href=\"https://bmcbioinformatics.biomedcentral.com/articles/10.1186/1471-2105-11-395\">BMC Bioinformatics, 11, 395 (2010)</a></li>"
        + "</ul></p>";
    ParameterSetupDialog dialog =
        new ParameterSetupDialog(parent, valueCheckRequired, this, message);
    dialog.setVisible(true);
    return dialog.getExitCode();
  }
}
