(ns casciidoc.core
	(:use [com.lithinos.amotoen.core :only [pegs lpegs post-process wrap-string pegasus]]))

(def letters "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

(def grammar {
	:Document '(* :Paragraph)
	:Paragraph '(* (| :Text :EOL))
	:Text '(* (| :Char :Whitespace))
	:EOL '(| \newline \return)
	:Whitespace '(| \space \tab)
	:Char (lpegs '| letters)
})

(defn asciidoc [root-node input] (pegasus root-node grammar (wrap-string input)))