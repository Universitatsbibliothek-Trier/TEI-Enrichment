# @author       René Ackels
# Copyright (c) 2024 Universität Trier

# This file is part of TEI-Enrichment.

# TEI-Enrichment is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# TEI-Enrichment is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http:#www.gnu.org/licenses/>.

# start JavaFX application
echo "TEI-Enrichment  Copyright (C) 2024  Universität Trier"
echo "This program comes with ABSOLUTELY NO WARRANTY; for details type 'show w'."
echo "This is free software, and you are welcome to redistribute it"
echo "under certain conditions; type 'show c' for details."
echo "------------------------------------------------------------------------"

script_dir=$(dirname $0)
cd "$script_dir/../"

mvn clean javafx:run
