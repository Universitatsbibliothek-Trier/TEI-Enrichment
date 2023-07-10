# @author       René Ackels
# Copyright (c) 2023 René Ackels

# This file is part of OCR-To-TEI.

# OCR-To-TEI is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# OCR-To-TEI is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

#!/bin/bash

# start Java program with hard coded paths
echo "OCR-To-TEI  Copyright (C) 2023  Universität Trier"
echo "This program comes with ABSOLUTELY NO WARRANTY; for details type 'show w'."
echo "This is free software, and you are welcome to redistribute it"
echo "under certain conditions; type 'show c' for details."
echo "------------------------------------------------------------------------"

script_dir=$(dirname $0)
cd "$script_dir/../"

# modsPath="/home/ackels/Dokumente/ocr-to-tei-pipeline-1/OCR-To-TEI/src/main/resources/modsFiles/hbz_metadaten_originalbaende/ah232-2_HT018904292_Bavariae_1656.xml"
# ocrPath="/home/ackels/Dokumente/NickBayern/pages/"
#ocrPath="/home/ackels/Dokumente/ocr-to-tei-pipeline-1/OCR-To-TEI/src/main/resources/ocrOutputFiles/nickbayern/"
# teiPathName="/home/ackels/Dokumente/ocr-to-tei-pipeline-1/TEI_CSV_Output/TEI_created_Band1.xml"

#mvn -q -e compile exec:java
mvn clean javafx:run
