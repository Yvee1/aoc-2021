with open("input.txt") as file:
	inpt = list(file.read().splitlines())

x = 0
d = 0
for line in inpt:
  (word, strn) = line.split()
  n = int(strn)
  if word == 'forward':
    x += n
  elif word == 'down':
    d += n
  elif word == 'up':
    d -= n
  
print(f"Part 1: {x * d}")

x = 0
d = 0
a = 0
for line in inpt:
  (word, strn) = line.split()
  n = int(strn)
  if word == 'forward':
    x += n
    d += n * a
  elif word == 'down':
    a += n
  elif word == 'up':
    a -= n

print(f"Part 2: {x * d}")