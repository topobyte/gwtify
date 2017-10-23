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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Gwtifier
{

	public void gwtify(Path input, Path output) throws ZipException,
			IOException
	{
		System.out.println(String.format("Gwtifing \"%s\" → \"%s\"", input,
				output));

		Set<Path> roots = findRoots(input);

		for (Path path : roots) {
			System.out.println("root: " + path);
		}

		ZipFile inZip = new ZipFile(input.toFile());
		OutputStream os = Files.newOutputStream(output);
		ZipOutputStream outZip = new ZipOutputStream(os);

		copy(inZip, outZip);
		inZip.close();
		outZip.close();
	}

	private Set<Path> findRoots(Path input) throws IOException
	{
		Set<Path> roots = new HashSet<>();

		ZipFile inZip = new ZipFile(input.toFile());
		Enumeration<? extends ZipEntry> entries = inZip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				Path p = Paths.get(entry.getName());
				roots.add(p.getName(0));
			}
		}
		inZip.close();

		roots.remove(Paths.get("META-INF"));
		return roots;
	}

	private void copy(ZipFile inZip, ZipOutputStream outZip) throws IOException
	{
		Enumeration<? extends ZipEntry> entries = inZip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (!entry.isDirectory()) {
				outZip.putNextEntry(entry);
				if (!entry.isDirectory()) {
					copy(inZip.getInputStream(entry), outZip);
				}
				outZip.closeEntry();
			}
		}
	}

	private void copy(InputStream input, OutputStream output)
			throws IOException
	{
		byte[] BUFFER = new byte[4096];
		int bytesRead;
		while ((bytesRead = input.read(BUFFER)) != -1) {
			output.write(BUFFER, 0, bytesRead);
		}
	}

}
