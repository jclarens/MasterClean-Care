<?php

namespace App\Http\Controllers;

use App\WorkTime;
use Illuminate\Http\Request;

class WorkTimeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return WorkTime::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();

        $workTime = WorkTime::create($data);

        return response()->json($workTime, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\WorkTime  $workTime
     * @return \Illuminate\Http\Response
     */
    public function show(WorkTime $workTime)
    {
        return $workTime;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\WorkTime  $workTime
     * @return \Illuminate\Http\Response
     */
    public function edit(WorkTime $workTime)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\WorkTime  $workTime
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, WorkTime $workTime)
    {
        $data = $request->all();

        if (array_key_exists('work_time', $data)) {
            $workTime->workTime = $data['work_time'];
        }
        
        $workTime->save();

        return response()->json($workTime, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\WorkTime  $workTime
     * @return \Illuminate\Http\Response
     */
    public function destroy(WorkTime $workTime)
    {
        $workTime->delete();

        return response()->json('Deleted', 200);
    }
}