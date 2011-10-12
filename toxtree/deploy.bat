mkdir dist
copy toxtree-app\target\Toxtree-2.5.1.jar dist
mkdir dist\ext
del dist\ext\*
copy toxtree-plugins\toxtree-cramer\target\*.jar dist\ext
copy toxtree-plugins\toxtree-cramer2\target\*.jar dist\ext
copy toxtree-plugins\toxtree-eye\target\*.jar dist\ext
copy toxtree-plugins\toxtree-kroes\target\*.jar dist\ext
copy toxtree-plugins\toxtree-mic\target\*.jar dist\ext
copy toxtree-plugins\toxtree-michaelacceptors\target\*.jar dist\ext
copy toxtree-plugins\toxtree-mutant\target\*.jar dist\ext
copy toxtree-plugins\toxtree-verhaar\target\*.jar dist\ext
copy toxtree-plugins\toxtree-sicret\target\*.jar dist\ext
copy toxtree-plugins\toxtree-smartcyp\target\*.jar dist\ext
copy toxtree-plugins\toxtree-skinsensitisation\target\*.jar dist\ext
copy toxtree-plugins\toxtree-biodegradation\target\*.jar dist\ext
;copy toxtree-plugins\toxtree-verhaar2\target\*.jar dist\ext
copy toxtree-plugins\toxtree-functional_groups\target\*.jar dist\ext
copy toxtree-plugins\toxtree-proteinbinding\target\*.jar dist\ext
copy toxtree-plugins\toxtree-moa\target\*.jar dist\ext