main :: IO ()
main = do 
  ints <- parseFile
  putStrLn $ "Part 1: " <> show (increases ints)
  putStrLn $ "Part 2: " <> show (increases (map sum (windowed 3 ints))) 

increases :: Ord a => [a] -> Int
increases xs = foldr (\(x, y) acc -> acc + if x < y then 1 else 0) 0 $ zip xs (tail xs)

windowed :: Int -> [a] -> [[a]]
windowed k xs = [ take k ys | i <- [0..length xs - k], let ys = drop i xs ]

parseFile :: IO [Int]
parseFile = map read . lines <$> readFile "input.txt"
