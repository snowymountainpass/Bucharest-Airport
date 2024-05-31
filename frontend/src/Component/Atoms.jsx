import {atom} from "jotai";

export const licensePlateAtom = atom('');
export const clientSecretAtom = atom('');
export const isPaidAtom = atom('');
export let isToastVisibleAtom = atom(false);