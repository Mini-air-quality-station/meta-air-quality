# ./dependency.sh [-g] [OPTION]... [RECIPE]...
# -g - generate dependencies again
# ./dependency.sh nano gcc  == python py_dependency.sh -t -r nano gcc task-depends.dot

tree="-t"
reverse="-r"
generate=0

for (( i = 1; i <= "$#"; i++ ))
do
    if [[ "${!i}" == -* && "${!i}" != "-g" ]]
    then
        tree=""
        reverse=""
    fi
    if [[ "${!i}" == "-g" ]]
    then
        generate=$i
    fi
done

if [[ ! -f task-depends.dot || $generate -gt 0 ]]
then
    make depends
fi

if [[ $generate > 0 ]]
then
    no_g="${@:1:$((generate - 1))} ${@:$((generate + 1))}"
    python py_dependency.py $no_g $tree $reverse task-depends.dot
else
    python py_dependency.py "$@" $tree $reverse task-depends.dot
fi
