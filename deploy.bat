mkdir dist
del dist\*
copy toxtree-app\toxtree-plugins.properties dist
copy toxtree-app\target\Toxtree-3.0.0-SNAPSHOT.jar dist
mkdir dist\ext
del dist\ext\*
copy toxtree-app\index.properties dist
copy toxtree-plugins\toxtree-ames\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-cramer\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-cramer2\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-eye\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-kroes\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-mic\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-michaelacceptors\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-mutant\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-verhaar\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-sicret\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-smartcyp\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-skinsensitisation\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-biodegradation\target\*3.0.0-SNAPSHOT.jar dist\ext
;copy toxtree-plugins\toxtree-verhaar2\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-functional_groups\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-proteinbinding\target\*3.0.0-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-dnabinding\target\*3.0.0-SNAPSHOT.jar dist\ext
rem copy toxtree-plugins\toxtree-moa\target\*.jar dist\ext