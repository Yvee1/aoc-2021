main :: IO ()
main = do
  input <- parseFile
  let gamma = binaryToInt . mostCommonBits $ input
  let epsilon =  (2 ^ length (head input) - 1) - gamma
  let p1 = gamma * epsilon
  putStrLn $ "Part 1: " <> show p1
  let p2 = oxygen input * co2 input
  putStrLn $ "Part 2: " <> show p2

stringToBinary :: String -> [Bool]
stringToBinary = map f
  where f '0' = False
        f '1' = True

boolToInt :: Bool -> Int
boolToInt False = 0
boolToInt True  = 1

binaryToInt :: [Bool] -> Int
binaryToInt = foldl (\acc x -> acc * 2 + boolToInt x) 0

mostCommonBits :: [String] -> [Bool]
mostCommonBits xs
  = map (if even n then (>= n `div` 2) else (> n `div` 2))
  . foldr (zipWith f) (repeat 0)
  $ xs
  where f bit acc = acc + read [bit]
        n = length xs

filterProcess :: ((Int, [Bool], String) -> Bool) -> [String] -> [Bool]
filterProcess = gFilterProcess 0

gFilterProcess :: Int -> ((Int, [Bool], String) -> Bool) -> [String] -> [Bool]
gFilterProcess _ _ []  = error "Nothing remaining after filtering"
gFilterProcess _ _ [s] = stringToBinary s
gFilterProcess i p xs = gFilterProcess (i+1) p (map thrd result)
  where result = filter p $ zip3 (repeat i) (repeat (mostCommonBits xs)) xs
        thrd (_, _, c) = c

oxygen :: [String] -> Int
oxygen = binaryToInt . filterProcess p
  where p (i, mcbs, xs) = read [xs !! i] == boolToInt (mcbs !! i)

co2 :: [String] -> Int
co2 = binaryToInt . filterProcess p
  where p (i, mcbs, xs) = (1 - read [xs !! i]) == boolToInt (mcbs !! i)

parseFile :: IO [String]
parseFile = lines <$> readFile "input.txt"
