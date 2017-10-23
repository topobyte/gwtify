// Copyright 2017 Sebastian Kuerten
//
// This file is part of gwtify.
//
// gwtify is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// gwtify is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with gwtify. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.gwtify;

import java.nio.file.Path;
import java.nio.file.Paths;

public class GwtifyJar
{

	public static void main(String[] args)
	{
		if (args.length != 2) {
			System.out.println("usage: gwtify <input> <output>");
			System.exit(1);
		}

		Path input = Paths.get(args[0]);
		Path output = Paths.get(args[1]);

		Gwtifier gwtifier = new Gwtifier();
		gwtifier.gwtify(input, output);
	}

}
