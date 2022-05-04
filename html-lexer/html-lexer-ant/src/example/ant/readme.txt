build
=====

 cd html-lexer
 ./gradlew jar


execute ant example
===================

 cd html-lexer/html-lexer-ant/src/example/ant
 ant -lib ../../../build/libs/html-lexer-ant-0.1.0.jar -lib ../../../../html-lexer-core/build/libs/html-lexer-core-0.1.0.jar -f example2.xml lower_tag
 