mkdir dist
rm dist/*
cp toxtree-app/toxtree-plugins.properties dist
cp toxtree-app/target/Toxtree-$1.jar dist
mkdir dist/ext
rm dist/ext/*
cp toxtree-app/index.properties dist/ext
cp toxtree-plugins/toxtree-ames/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-cramer/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-cramer2/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-eye/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-kroes/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-mic/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-michaelacceptors/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-mutant/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-verhaar/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-sicret/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-smartcyp/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-skinsensitisation/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-biodegradation/target/*$1.jar dist/ext
#cp toxtree-plugins/toxtree-verhaar2/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-functional_groups/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-proteinbinding/target/*$1.jar dist/ext
cp toxtree-plugins/toxtree-dnabinding/target/*$1.jar dist/ext
# cp toxtree-plugins/toxtree-moa/target/*.jar dist/ext

