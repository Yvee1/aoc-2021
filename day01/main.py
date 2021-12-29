with open("input.txt") as file:
	inpt = list(map(int, file.read().splitlines()))

def increases(ints):
  result = 0
  for i in range(len(ints))[1:]:
    if (ints[i-1] < ints[i]):
      result += 1
  return result

def windowedIncreases(ints, k):
  result = 0
  for i in range(len(ints)-k):
    # To check whether a_i + ... + a_{i+k-1} < a_{i+1} + ... + a_{i+k}
    # we only need to check if a_i < a_{i+k}
    if (ints[i] < ints[i+k]):
      result += 1
  return result

print(f'Part 1: {increases(inpt)}')
print(f'Part 2: {windowedIncreases(inpt, 3)}')
