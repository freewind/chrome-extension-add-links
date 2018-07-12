Chrome Extension Add Links
=======================

Add predefined links to specified text.

Build:

```
./gradlew compileKotlin2Js
cd extension
npm install
```

Then add `extension` directory to Chrome extensions.

Test:
-----

```
npm install -g http-server
http-server ./public -o
```

On the page, the keywords should be replaced by links.