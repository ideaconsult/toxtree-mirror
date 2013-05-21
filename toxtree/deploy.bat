mkdir dist
copy toxtree-app\target\Toxtree-2.5.9-SNAPSHOT.jar dist
mkdir dist\ext
del dist\ext\*
copy toxtree-plugins\toxtree-ames\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-cramer\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-cramer2\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-eye\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-kroes\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-mic\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-michaelacceptors\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-mutant\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-verhaar\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-sicret\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-smartcyp\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-skinsensitisation\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-biodegradation\target\*2.5.9-SNAPSHOT.jar dist\ext
;copy toxtree-plugins\toxtree-verhaar2\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-functional_groups\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-proteinbinding\target\*2.5.9-SNAPSHOT.jar dist\ext
copy toxtree-plugins\toxtree-dnabinding\target\*2.5.9-SNAPSHOT.jar dist\ext
rem copy toxtree-plugins\toxtree-moa\target\*.jar dist\ext