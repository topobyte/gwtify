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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Gwtifier
{

	public void gwtify(Path input, Path output) throws ZipException,
			IOException
	{
		System.out.println(String.format("Gwtifing \"%s\" → \"%s\"", input,
				output));

		Set<Path> roots = new HashSet<>();

		ZipFile zip = new ZipFile(input.toFile());
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				Path p = Paths.get(entry.getName());
				roots.add(p.getName(0));
			}
		}
		zip.close();

		roots.remove(Paths.get("META-INF"));

		for (Path path : roots) {
			System.out.println("root: " + path);
		}
	}

}
