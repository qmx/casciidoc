(ns casciidoc.core
	(:use [com.lithinos.amotoen.core :only [pegs lpegs post-process wrap-string pegasus]]))

(defn all-letters [charset-name]
  (let [ce (-> charset-name java.nio.charset.Charset/forName .newEncoder)]
    (->> (range 0 (int Character/MAX_VALUE)) (map char)
         (filter #(and (.canEncode ce %) (Character/isLetter %))))))

(def letters (apply str (all-letters "utf-8")))

(def grammar {
	:Document '(* [:Paragraph :EOL])
	:Paragraph '(* :Text)
	:Text '(*  (| :Char :Whitespace))
	:EOL '(| \newline \return)
	:Whitespace '(| \space \tab)
	:Char (lpegs '| letters)
})

(defn asciidoc [root-node input] (pegasus root-node grammar (wrap-string input)))