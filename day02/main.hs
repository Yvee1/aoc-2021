data Movement = Forward Int
              | Down Int
              | Up Int
  deriving Show

main :: IO ()
main = do
  mvmnts <- parseFile
  putStrLn $ "Part 1: " <> (show ((uncurry (*)) (positionDepth mvmnts)))
  let (p, d, _) = positionDepthAim mvmnts
  putStrLn $ "Part 2: " <> (show (p * d))

positionDepth :: [Movement] -> (Int, Int)
positionDepth = foldr f (0, 0)
  where f (Forward dx) (x, d) = (x + dx, d     )
        f (Down    dd) (x, d) = (x     , d + dd)
        f (Up      dd) (x, d) = (x     , d - dd)

positionDepthAim :: [Movement] -> (Int, Int, Int)
positionDepthAim = foldr f (0, 0, 0)
  where f (Forward dx) (x, d, a) = (x + dx, d + dx * a, a)
        f (Down    da) (x, d, a) = (x, d, a + da)
        f (Up      da) (x, d, a) = (x, d, a - da)

parseFile :: IO [Movement]
parseFile = reverse . map parse . lines <$> readFile "input.txt"

parse :: String -> Movement
parse xs = let ([dir, amnt]) = words xs in
  case dir of
  "forward" -> Forward $ read amnt
  "down"    -> Down $ read amnt
  "up"      -> Up $ read amnt