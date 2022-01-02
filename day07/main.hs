import Data.List
import Data.List.Split

main :: IO ()
main = do
  input <- getInput
  let sorted = sort input
  print $ part1 sorted
  -- Search for largest value, start at mean
  print $ part2 (mean sorted) sorted

-- Precondition: xs is sorted
part1 :: [Int] -> Int
part1 xs = sum . map (abs . subtract m) $ xs
  where m = median xs

part2 :: Int -> [Int] -> Int
part2 m xs
  | dl > dn && dn < dr = dn
  | dl < dn && dn < dr = part2 (m - 1) xs
  | dl > dn && dn > dr = part2 (m + 1) xs
  | otherwise = error "start value should not be the maximum"
  where
    dl = part2dist (m - 1) xs
    dn = part2dist m xs
    dr = part2dist (m + 1) xs

part2dist :: Int -> [Int] -> Int
part2dist m = sum . map (\x -> let d = abs(x - m) in (d * (d+1)) `div` 2)

mean :: [Int] -> Int
mean xs = round $ fromIntegral (sum xs) / fromIntegral (length xs)

-- Precondition: xs is sorted
median :: [Int] -> Int
median xs = let n = length xs in
  if odd n 
    then xs !! (n `div` 2)
    else (xs !! (n `div` 2 - 1) + xs !! (n `div` 2 - 1)) `div` 2

getInput :: IO [Int]
getInput = parseFile "input.txt"

getTest :: IO [Int]
getTest = parseFile "test.txt"

parseFile :: FilePath -> IO [Int]
parseFile fp = map read . splitOn "," <$> readFile fp
