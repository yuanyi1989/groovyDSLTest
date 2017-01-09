def show = {println it}
def squrt = {it * it}

def please(Closure closure){
    [the : {action -> [of : {value -> closure(action(value))}]}]
}

please show the squrt of 100

def map = [add : {op -> [minus : {value -> op(value)-value}]}]

def value = map.add squrt minus 10
println value



def xy (Closure action){
    action
}
def printx = {println it}
def aa = xy(printx)(2)
println aa