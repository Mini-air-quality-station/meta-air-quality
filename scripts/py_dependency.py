import sys
from pathlib import Path
from io import StringIO

def add_to_dict(dictionary, key, val):
        if key in dictionary:
            dictionary[key].add(val)
        else:
            dictionary[key] = {val}

def print_help():
    print("dependency.py [OPTION]... [RECIPE]... TASK_DEPENDS")
    print("dependency.py [OPTION]... [-t|--tree] RECIPE TASK_DEPENDS")
    print("dependency.py [OPTION]... [-a|--all] TASK_DEPENDS")
    print("SUMMARY:")
    print("\tFind dependencies of recipes or packages")
    print("\tRECIPE -> DEPENDENCY... - RECIPE needs DEPENDENCIES to be built")
    print("\tRECIPE <- DEPENDANT... - --reverse: DEPENDANT needs RECIPE to be built")
    print("\tTASK_DEPENDS can be piped, e.g.")
    print("\tcat task-depends.dot | python dependency.py recipe")
    print("OPTION:")
    print("\t-h --help     - print this help")
    print("\t-r --reverse  - reverse dependencies")
    print("\t-t --tree     - prints all dependecies starting with RECIPE")
    print("\t-a --all      - prints dependencies of all recipes(without duplicates).")
    print("\t                Ignores RECIPE...")
    print("\t              - -a and -t are mutually exclusive")
    
def replace_opt(opt):
    opts = {
        "--help": "h",
        "--reverse": "r",
        "--tree": "t",
        "--all": "a",
        }
    if opt.startswith("--"):
        return opts[opt] if opt in opts else ""
    else:
        return opt[1:]
        

def main():
    opts = ''.join([replace_opt(opt) for opt in sys.argv[1:] if opt.startswith("-")])
    args = [arg for arg in sys.argv[1:] if not arg.startswith("-")]
    reverse = True if "r" in opts else False
    print_all = True if "a" in opts else False
    tree = True if "t" in opts else False
    if "h" in opts or (tree and print_all) or not args:
        print_help()
        return
    dependency_map = {}
    if sys.stdin.isatty():
        try:
            input_string = Path(f"{args[-1]}", encoding="utf-8").read_text()
        except Exception as ex:
            print(ex)
            return
        recipes = args[:-1]
    
    else:
        recipes = args
        input_string = sys.stdin.read()
    
    for line_num, line_str in enumerate(StringIO(input_string)):
        try:
            if "->" in line_str:
                strings = line_str.split('"')
                left = strings[1].split('.')[0]
                right = strings[3].split('.')[0]
                if reverse:
                    left, right = right, left
                if left != right and (print_all or tree or left in recipes):
                    add_to_dict(dependency_map, left, right)
        except:
            print("Wrong format on line {line_num}. Detected '->'")
    
    if not tree:
        for key, vals in dependency_map.items():
            print(f'{key} {"<-" if reverse else "->"} ', end='')
            for val in vals:
                print(f'{val}, ', end='')
            print("\n")
    else:
        current_level = {*recipes[:1]}
        next_level = set()
        already_done = set(current_level)
        level = 1
        while current_level:
            print(level, end=': ')
            for recipe in current_level:
                print(recipe, end=', ')
                if recipe in dependency_map:
                    recipe_set = dependency_map.pop(recipe) - already_done
                    already_done.update(recipe_set)
                    next_level.update(recipe_set)
            current_level, next_level = next_level, set()
            level += 1
            print("\n")


if __name__ == "__main__":
    main()
