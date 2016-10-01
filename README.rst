====================
gitbucket-rst-plugin
====================

.. image:: https://travis-ci.org/amuramatsu/gitbucket-rst-plugin.svg
    :alt: Build Status
    :target: https://travis-ci.org/amuramatsu/gitbucket-rst-plugin

plugin-version
  0.1.0
gitbucket-bincompat-version
  4.0

A `Git Bucket <https://github.com/gitbucket/gitbucket>`_ plug-in that
provides ReSTructured text rendering capabilities. All ``.rst`` files
will be automatically rendered as ReSTructured text, supporting most
features of the `LAIKA <http://planet42.github.io/Laika/>`_.

Download
---------

You can download a precompiled version from the
`Release <https://github.com/amuramatsu/gitbucket-rst-plugin/releases>`_.

Build from source
-----------------

.. code-block:: none

    sbt clean package

The built package is located at
``target/scala-2.11/gitbucket-{gitbucket-bincompat-version}-rst-plugin-{plugin-version}.jar``.

.. code-block:: none

    sbt assembly

This makes the assembly package
``target/scala-2.11/gitbucket-{gitbucket-bincompat-version}-rst-plugin-assembly-{plugin-version}.jar``
for deployment.

Installation
------------

Download
``gitbucket-{gitbucket-bincompat-version}-rst-plugin-assembly-{plugin-version}.jar``
or build the assembly package JAR file from source and copy it into
``GITBUCKET_HOME/plugins``. If you have older versions of this plugin in
this directory, you must delete them. Then restart GitBucket. That's it.

License
-------

This plugin is published under the Apache License, version 2.0.

Thanks
------

This plug-in is based on
`asciidoctor plug-in <https://github.com/asciidoctor/gitbucket-asciidoctor-plugin>`_.

This plug-in uses `LAIKA <http://planet42.github.io/Laika/>`_ as ReSTructued
text renderer.

ChangeLog
---------

gitbucket-rst-plugin 0.1.0 - 2016-10-02
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- Initial release