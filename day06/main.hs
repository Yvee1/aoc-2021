import Data.List ( group, sort )
import Data.List.Split ( splitOn )
import Data.Map.Strict ( fromDistinctAscList, findWithDefault )

main :: IO ()
main = do
  input <- getInput
  print $ solve 80 input
  print $ solve 256 input

solve :: Int -> [Int] -> Integer
solve n xs = sum $ iterate updateGrouped start !! n
  where thing = fromDistinctAscList . map (\ys -> (head ys, fromIntegral (length ys))) . group . sort $ xs
        start = [findWithDefault 0 i thing | i <- [0..8]]

updateGrouped :: Num a => [a] -> [a]
updateGrouped [c0, c1, c2, c3, c4, c5, c6, c7, c8] = [c1, c2, c3, c4, c5, c6, c0 + c7, c8, c0]

getInput :: IO [Int]
getInput = parseFile "input.txt"

parseFile :: FilePath -> IO [Int]
parseFile fp =  map read . splitOn "," <$> readFile fp
