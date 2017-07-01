#deploy 3.2.0
mkdir dist
del dist\*
copy toxtree-app\toxtree-plugins.properties dist
copy toxtree-app\target\Toxtree-%1.jar dist
mkdir dist\ext
del dist\ext\*
copy toxtree-app\index.properties dist\ext
copy toxtree-plugins\toxtree-ames\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-cramer\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-cramer2\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-eye\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-kroes\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-mic\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-michaelacceptors\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-mutant\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-verhaar\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-sicret\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-smartcyp\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-skinsensitisation\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-biodegradation\target\*%1.jar dist\ext
;copy toxtree-plugins\toxtree-verhaar2\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-functional_groups\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-proteinbinding\target\*%1.jar dist\ext
copy toxtree-plugins\toxtree-dnabinding\target\*%1.jar dist\ext
rem copy toxtree-plugins\toxtree-moa\target\*.jar dist\ext